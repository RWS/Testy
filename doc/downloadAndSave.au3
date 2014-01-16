#include <File.au3>
Local $hFile = FileOpen(@ScriptDir & "\downloadAndSave.log", 1)
$result = 1
$txt = ""
Sleep(200)
;$activeWindowTitle = WinGetTitle(WinWait("Opening", " ", 3))
$activeWindowTitle = WinGetTitle("[ACTIVE]")
WinFlash($activeWindowTitle, "", 1, 50) ; Just to Flash the window
_FileWriteLog($hFile, "ActiveWindowTitle1='" & $activeWindowTitle & "'")
Send("!s");
Send("{ENTER}")
$activeWindowTitle = WinGetTitle(WinWait("[CLASS:#32770]", " ", 3))
_FileWriteLog($hFile, "ActiveWindowTitle2='" & $activeWindowTitle & "'")
WinFlash($activeWindowTitle, "", 1, 50) ; Just to Flash the window
$txt = ControlGetText($activeWindowTitle, "", "[CLASS:Edit; INSTANCE:1]")
_FileWriteLog($hFile, "txt='" & $txt & "'")
$result1 = ControlSetText($activeWindowTitle, "", "Edit1", @ScriptDir &"\"& $txt)
$result2 = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:1]")
$result3 = WinWaitClose($activeWindowTitle, " ", 3)
;_FileWriteLog($hFile, "$result1='" & $result1 & "'")
;_FileWriteLog($hFile, "$result2='" & $result2 & "'")
_FileWriteLog($hFile, "$result3='" & $result3 & "'")
If $result1 = 1 And $result2 = 1 And $result3 = 1 Then
   $result = 0
Else
   ControlClick("[CLASS:#32770]", "", "[CLASS:Button; INSTANCE:2]")
   Sleep(100)
   WinClose("[ACTIVE]", "")
   $result = 1
EndIf
FileClose($hFile)
Exit $result