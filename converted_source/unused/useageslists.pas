unit USWorld;

interface

  uses Classes;

type

  TWorld = class;
  TSRule = class;

  TSVariableState = (kPresent, kAbsent);

	TSVariable = class(TObject)
    world: TWorld;
    phrase: string;
    state: TSVariableState;
    contextUseages: TList;
    requirementsUseages: TList;
    commandUseages: TList;
    moveUseages: TList;
    changesUseages: TList;
    graphUseages: TList;
    containsMacro: boolean;
    madeByMacro: boolean;
		procedure setPhrase(const aPhrase: string);
		procedure setState(newState: TSVariableState);
		function getState: TSVariableState;
		function expandPhraseForMacros: string;
		function doMacro(const macro: string): string;
    procedure contextUseageAdd(rule: TSRule);
    procedure requirementsUseageAdd(rule: TSRule);
    procedure commandUseageAdd(rule: TSRule);
    procedure moveUseageAdd(rule: TSRule);
    procedure changesUseageAdd(rule: TSRule);
    procedure contextUseageRemove(rule: TSRule);
    procedure requirementsUseageRemove(rule: TSRule);
    procedure commandUseageRemove(rule: TSRule);
    procedure moveUseageRemove(rule: TSRule);
    procedure changesUseageRemove(rule: TSRule);
    end;

  TSDesiredStateVariableWrapper = class(TObject)
    variable: TSVariable;
    desiredState: TSVariableState;
   	function leader: string;
    end;

  TSRequiredStateVariableWrapper = class(TSDesiredStateVariableWrapper)
    end;

  TSChangedVariableWrapper = class(TObject)
    variable: TSVariable;
    oldState: TSVariableState;
    newState: TSVariableState;
    constructor createWithVariableNewState(variable: TSVariable; newState: TSVariableState);
    procedure doChange;
    procedure undoChange;
    end;

  TSRule = class(TObject)
  	world: TWorld;
    context: TSVariable;
    requirements: TList;
    command: TSVariable;
    reply: string;
    move: TSVariable;
    changes: TList;
    available: boolean;
    requirementsString: string;
    changesString: string;
		constructor create;
		destructor destroy; override;
		procedure setContext(const aString: string);
		procedure setRequirements(const aString: string);
		procedure setCommand(const aString: string);
		procedure setReply(const aString: string);
		procedure setMove(const aString: string);
		procedure setChanges(const aString: string);
		procedure compile(const aString: string; list: TList);
		function decompile(list: TList): string;
    function decompileRequirements: string;
    function decompileChanges: string;
		procedure updateAvailable;
		procedure recordReplyMoveChanges(changedVariablesList: TList; var totalReply: string; var contextToFocusTo: TSVariable);
		procedure setTextForField(col: integer; const text: string);
 		function getTextForField(col: integer): string;
		class function headerForField(col: integer): string;
    end;

	TWorld = class(TObject)
  	emptyEntry: TSVariable;
    variables: TList;
    rules: TList;
    focus: TSVariable;
    previousFocus: TSVariable;
    firstCommandDoneForLastCommandPhrase: integer;
		constructor Create;
		procedure resetVariablesAndRules;
		destructor Destroy; override;
		function newRule: TSRule;
		function findVariable(const aString: string): TSVariable;
		function findOrCreateVariable(const aString: string; madeByMacro: boolean): TSVariable;
		function loadFromFile(const name: string): boolean;
		procedure saveToFile(const name: string);
		procedure updateAvailable;
		procedure setFocusTo(contextToFocusOn: TSVariable);
    end;

  // need to be sequence from zero
	const
  	kRuleContext = 0;
		kRuleCommand = 1;
		kRuleReply = 2;
		kRuleMove = 3;
		kRuleRequirements = 4;
		kRuleChanges = 5;

implementation

uses USMain, SysUtils, USConsoleForm;

//////////////////// TSVariable ///////////////////////

procedure TSVariable.setPhrase(const aPhrase: string);
  begin
  containsMacro := false;
  if aPhrase = '' then
    begin
    phrase := aPhrase;
    exit;
    end;
  if pos('{', aPhrase) <> 0 then
  	containsMacro := true;
  phrase := aPhrase;
  end;

procedure TSVariable.setState(newState: TSVariableState);
  var
  	expandedPhrase: string;
    variable: TSVariable;
  begin
  if not self.containsMacro then begin state := newState; exit; end;
  expandedPhrase := self.expandPhraseForMacros;
  variable := world.findOrCreateVariable(expandedPhrase, true);
  variable.state := newState;  // don't allow circular references
  end;

function TSVariable.getState: TSVariableState;
  var
  	expandedPhrase: string;
    variable: TSVariable;
  begin
  if not self.containsMacro then begin result := state; exit; end;
  expandedPhrase := self.expandPhraseForMacros;
  variable := world.findVariable(expandedPhrase);
  if variable = nil then begin result := kAbsent; exit; end;
  result := variable.state;  // don't allow circular references
  end;

function TSVariable.expandPhraseForMacros: string;
  var
    remaining, macro: string;
    startPosition, endPosition, wholeLength: integer;
  begin
  remaining := phrase;
  result := '';
  wholeLength := Length(remaining);
  while (Length(remaining) > 0) do
    begin
    startPosition := pos('{', remaining);
    if startPosition > 0 then
      begin
      result := result + Copy(remaining, 1, startPosition - 1);
      remaining := Copy(remaining, startPosition + 1, wholeLength);
      end
    else
      begin
      result := result + remaining;
      exit;
      end;
    endPosition := pos('}', remaining);
    if endPosition = 0 then
      begin
      result := result + '{' + remaining; // error
      exit;
      end;
    macro := Copy(remaining, 1, endPosition - 1);
    remaining := Copy(remaining, endPosition + 1, wholeLength);
    result := result + self.doMacro(Trim(macro));
    end;
  end;

function TSVariable.doMacro(const macro: string): string;
  begin
  if macro = 'location' then begin result := world.focus.phrase; exit; end;
  if macro = 'previous location' then begin result := world.previousFocus.phrase; exit; end;
  if macro = 'focus' then begin result := world.focus.phrase; exit; end;
  if macro = 'previous focus' then begin result := world.previousFocus.phrase; exit; end;
  // unknown
  result := '{' + macro + '}';
  end;

procedure TSVariable.contextUseageAdd(rule: TSRule);
  begin
  contextUseages.add(rule);
  end;

procedure TSVariable.requirementsUseageAdd(rule: TSRule);
  begin
  requirementsUseages.add(rule);
  end;

procedure TSVariable.commandUseageAdd(rule: TSRule);
  begin
  commandUseages.add(rule);
  end;

procedure TSVariable.moveUseageAdd(rule: TSRule);
  begin
  moveUseages.add(rule);
  end;

procedure TSVariable.changesUseageAdd(rule: TSRule);
  begin
  changesUseages.add(rule);
  end;

procedure TSVariable.contextUseageRemove(rule: TSRule);
  begin
  contextUseages.Remove(rule);
  end;

procedure TSVariable.requirementsUseageRemove(rule: TSRule);
  begin
  requirementsUseages.Remove(rule);
  end;

procedure TSVariable.commandUseageRemove(rule: TSRule);
  begin
  commandUseages.Remove(rule);
  end;

procedure TSVariable.moveUseageRemove(rule: TSRule);
  begin
  moveUseages.Remove(rule);
  end;

procedure TSVariable.changesUseageRemove(rule: TSRule);
  begin
  changesUseages.Remove(rule);
  end;

// PDF PORT -- added missing "end."
end.