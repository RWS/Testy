#include <File.au3>
Local $hFile = FileOpen(@ScriptDir & "\upload.log", 1)
Local $result = 1
Sleep(400)
$activeWindow = WinWaitActive("[TITLE:File; CLASS:#32770]", " ", 6)
If WinExists($activeWindow) Then
   _FileWriteLog($hFile, "File$activeWindow='" & $activeWindow & "'")
Else
$activeWindow = WinWaitActive("[TITLE:Open; CLASS:#32770]", "", 3)
_FileWriteLog($hFile, "Open$activeWindow='" & $activeWindow & "'")
EndIf
$activeWindowTitle = WinGetTitle($activeWindow)
WinFlash($activeWindowTitle, "", 1, 50) ; Just to Flash the window
;ControlSetText($activeWindowTitle, "", "Edit1", "D:\Mill\src\test\resources\file\export.xml")
ControlSetText($activeWindowTitle, "", "Edit1", $CmdLine[1])
$save = ControlCommand($activeWindowTitle, "", "Button1", "IsVisible", "")
_FileWriteLog($hFile, "---save='" & $save & "'")
If ($save = 1) Then
   ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:1]")
   $result = 0
   _FileWriteLog($hFile, "saveDefault='" & $save & "'")
Else
   ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:2]") ;Pentru browserul straniu de la BeGlobal
   $result = 0
   _FileWriteLog($hFile, "saveStraniu='" & $save & "'")
EndIf
Sleep(300)
_FileWriteLog($hFile, "win='" & $activeWindowTitle & "'")
_FileWriteLog($hFile, "save='" & $result & "'")
FileClose($hFile)
Exit $result AND WinWaitNotActive($activeWindowTitle, "", 3)