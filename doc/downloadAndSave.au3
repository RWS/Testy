#include <File.au3>
Local $hFile = FileOpen(@ScriptDir & "\downloadAndSave.log", 1)
$result = 1
$txt = ""
Sleep(200)
$activeWindow = WinWaitActive("[TITLE:Opening; CLASS:MozillaDialogClass]", "", 3)
$activeWindowTitle = WinGetTitle($activeWindow)
WinFlash($activeWindowTitle, "", 2, 50) ; Just to Flash the window
_FileWriteLog($hFile, "ActiveWindowTitle1='" & $activeWindowTitle & "'")
If WinExists($activeWindow) Then
   Sleep(20)
   Send("!s");
   Send("{ENTER}")
   $activeWindow = WinWaitActive("[TITLE:Enter; CLASS:#32770]", "", 3)
   $activeWindowTitle = WinGetTitle($activeWindow)
   _FileWriteLog($hFile, "ActiveWindowTitle2='" & $activeWindowTitle & "'")
   WinFlash($activeWindowTitle, "", 2, 50) ; Just to Flash the window
   $txt = ControlGetText($activeWindowTitle, "", "[CLASS:Edit; INSTANCE:1]")
   _FileWriteLog($hFile, "txt='" & $txt & "'")
   $result1 = ControlSetText($activeWindowTitle, "", "Edit1", @ScriptDir &"\"& $txt)
   $result2 = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:1]")
   $result3 = WinWaitClose($activeWindowTitle, " ", 3)
   _FileWriteLog($hFile, "$result3='" & $result3 & "'")
   If $result1 = 1 And $result2 = 1 And $result3 = 1 Then
	  $result = 0
   Else
	  ControlClick("[CLASS:#32770]", "", "[CLASS:Button; INSTANCE:2]")
	  Sleep(100)
	  WinClose("[ACTIVE]", "")
	  $result = 1
   EndIf
EndIf
FileClose($hFile)
Exit $result