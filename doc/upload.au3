#include <File.au3>
FileDelete(@ScriptDir & "\upload.log")
Local $hFile = FileOpen(@ScriptDir & "\upload.log", 1)
Local $count = 0
Local $result = 1
Sleep(400)
; Verific daca vereastra de upload este FireFox
If WinExists("[TITLE:File; CLASS:#32770]") Then
    $activeWindow = WinWait("[TITLE:File; CLASS:#32770]", " ", 6)
    WinActivate($activeWindow)
    _FileWriteLog($hFile, "File$activeWindow='" & $activeWindow & "'")
Else ; In caz contrar e Chrome
    $activeWindow = WinWait("[TITLE:Open; CLASS:#32770]", "", 3)
    WinActivate($activeWindow)
    _FileWriteLog($hFile, "Open$activeWindow='" & $activeWindow & "'")
EndIf
$activeWindowTitle = WinGetTitle($activeWindow)
WinFlash($activeWindowTitle, "", 1, 50) ; Just to Flash the window

Do
   _FileWriteLog($hFile, "$activeWindowTitle00='" & $activeWindowTitle & "'")
   If $count > 10 Then
	  $result = 1
	  ExitLoop
	EndIf
	Sleep(100)
    $count = $count + 1
Until ControlShow($activeWindowTitle, "", "Edit1") = 1
Local $edit = ControlSetText($activeWindowTitle, "", "Edit1", $CmdLine[1])
;Local $edit = ControlSetText($activeWindowTitle, "", "Edit1", "D:\Mill\src\test\resources\file\export.xml")
_FileWriteLog($hFile, "$edit='" & $edit & "'")

$count = 0
Do
    _FileWriteLog($hFile, "$activeWindowTitle4='" & $activeWindowTitle & "'")
	If $count > 10 Then
	   $result = 1
	  ExitLoop
	EndIf
    Sleep(100)
    $count = $count + 1
	$result = 0
Until ControlShow($activeWindowTitle, "", "Button1") = 1

Local $click;
If (ControlShow($activeWindowTitle, "", "Button1") = 1) Then
   $click = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:1]")
   $result = 0
   _FileWriteLog($hFile, "$clickDefault='" & $click & "'")
ElseIf (ControlShow($activeWindowTitle, "", "Button2") = 1) Then
   $click = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:2]") ;Pentru browserul straniu de la BeGlobal
   $result = 0
   _FileWriteLog($hFile, "$clickStraniu='" & $click =  & "'")
Else
   $result = 1
   _FileWriteLog($hFile, "Error! Nu am gasit nici un button!!!!")
EndIf
Sleep(300)
$close = WinWaitClose($activeWindowTitle, "", 3)
_FileWriteLog($hFile, "win='" & $activeWindowTitle & "'")
_FileWriteLog($hFile, "$close='" & $close & "'")
_FileWriteLog($hFile, "$result='" & $result & "'")
_FileWriteLog($hFile, "------------------------- New Upload --------------------------")
FileClose($hFile)
Exit $result