#include <File.au3>
Local $hFile = FileOpen(@ScriptDir & "\downloadAndSave.log", 1)
$result = 1
$txt = ""
Sleep(200)
$activeWindow = WinWait("[TITLE:Opening; CLASS:MozillaDialogClass]", "", 3)
WinActivate($activeWindow)
$activeWindowTitle = WinGetTitle($activeWindow)
WinFlash($activeWindowTitle, "", 2, 50) ; Just to Flash the window
_FileWriteLog($hFile, "ActiveWindowTitle1='" & $activeWindowTitle & "'")
If WinExists($activeWindow) Then
   Sleep(200)
   Send("!s");
   Send("{ENTER}")
   Sleep(100)
   $activeWindow = WinWait("[TITLE:Enter; CLASS:#32770]", "", 3)
   WinActivate($activeWindow)
   Sleep(200)
   If NOT WinExists($activeWindow) Then
       Sleep(100)
       Send("!s");
       Send("{ENTER}")
       Sleep(100)
       $activeWindow = WinWait("[TITLE:Enter; CLASS:#32770]", "", 3)
       WinActivate($activeWindow)
   EndIf
   $activeWindowTitle = WinGetTitle($activeWindow)
   _FileWriteLog($hFile, "ActiveWindowTitle2='" & $activeWindowTitle & "'")
   WinFlash($activeWindowTitle, "", 2, 50) ; Just to Flash the window
   $txt = ControlGetText($activeWindowTitle, "", "[CLASS:Edit; INSTANCE:1]")
   _FileWriteLog($hFile, "txt='" & $txt & "'")
   $allText = ControlSetText($activeWindowTitle, "", "Edit1", @ScriptDir &"\"& $txt)
   $click = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:1]")
   $close = WinWaitClose($activeWindowTitle, " ", 3)
   Sleep(200)
   WinClose($activeWindowTitle, "")
   _FileWriteLog($hFile, "$close='" & $close & "'")
   If $allText = 1 And $click = 1 And $close = 1 Then
      _FileWriteLog($hFile, "Este bine='" & $close & "'")
	  $result = 0
      _FileWriteLog($hFile, "Este bine2='" & $result & "'")
   Else
	  ControlClick("[CLASS:#32770]", "", "[CLASS:Button; INSTANCE:2]")
	  Sleep(100)
	  WinClose("[ACTIVE]", "")
	  _FileWriteLog($hFile, "nu este bine='" & $close & "'")
	  $result = 1
   EndIf
EndIf
FileClose($hFile)
Exit $result