#include <File.au3>
FileDelete(@ScriptDir & "\upload.log")
Local $hFile = FileOpen(@ScriptDir & "\upload.log", 1)
Local $result = 1
Sleep(400)
; Verific daca vereastra de upload este FireFox
If WinExists("[TITLE:File; CLASS:#32770]") Then
    $activeWindow = WinWait("[TITLE:File; CLASS:#32770]", " ", 6)
    WinActivate($activeWindow)
    ;If WinExists($activeWindow) Then
    _FileWriteLog($hFile, "File$activeWindow='" & $activeWindow & "'")
Else ; In caz contrar e Chrome
    $activeWindow = WinWait("[TITLE:Open; CLASS:#32770]", "", 3)
    WinActivate($activeWindow)
    _FileWriteLog($hFile, "Open$activeWindow='" & $activeWindow & "'")
EndIf
$activeWindowTitle = WinGetTitle($activeWindow)
WinFlash($activeWindowTitle, "", 1, 50) ; Just to Flash the window
;ControlSetText($activeWindowTitle, "", "Edit1", "D:\Mill\src\test\resources\file\export.xml")
ControlSetText($activeWindowTitle, "", "Edit1", $CmdLine[1])
$save = ControlCommand($activeWindowTitle, "", "Button1", "IsVisible", "")
_FileWriteLog($hFile, "---save='" & $save & "'")
If ($save = 1) Then
   $click = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:1]")
   $result = 0
   _FileWriteLog($hFile, "saveDefault='" & $save & "'")
   _FileWriteLog($hFile, "$clickDefault='" & $click & "'")
Else
   $click = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:2]") ;Pentru browserul straniu de la BeGlobal
   $result = 0
   _FileWriteLog($hFile, "saveStraniu='" & $save & "'")
   _FileWriteLog($hFile, "$clickStraniu = ='" & $click =  & "'")
EndIf
Sleep(300)
$close = WinWaitClose($activeWindowTitle, "", 3)
_FileWriteLog($hFile, "win='" & $activeWindowTitle & "'")
_FileWriteLog($hFile, "$close='" & $close & "'")
_FileWriteLog($hFile, "$result='" & $result & "'")
_FileWriteLog($hFile, "------------------------- New Upload --------------------------")
FileClose($hFile)
Exit $result