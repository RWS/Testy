$result = 1
$txt = ""
WinWait($CmdLine[2])
WinActivate($CmdLine[2],"")
WinFlash($CmdLine[2], "", 1, 50)
If (StringCompare("Opening", $CmdLine[2], 0) = 0) Then ;For FF
   Send("!s");
   Send("{ENTER}")
   WinWait("Enter name of file to save to…")
   WinActivate("Enter name of file to save to…","")
   WinFlash("Enter name of file to save to…", "", 1, 50)
   $txt = ControlGetText("Enter name of file to save to…", "", "[CLASS:Edit; INSTANCE:1]")
   If (StringCompare($CmdLine[1], $txt, 0) = 0) Then
	  $result = 0
   EndIf
   ControlClick("Enter name of file to save to…", "", "[CLASS:Button; INSTANCE:2]")
   WinWaitNotActive("Enter name of file to save to…")
EndIf

If (StringCompare("Save As", $CmdLine[2], 0) = 0) Then ; For Chrome
   $txt = ControlGetText($CmdLine[2], "", "[CLASS:Edit; INSTANCE:1]")
   If (StringCompare($CmdLine[1], $txt, 0) = 0) Then
	  $result = 0
   EndIf
   ControlClick($CmdLine[2], "", "[CLASS:Button; INSTANCE:2]")
   WinWaitNotActive($CmdLine[2])
EndIf
Exit $txt