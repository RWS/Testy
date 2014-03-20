#include <File.au3>
FileDelete(@ScriptDir & "\downloadAndSave.log")
Local $hFile = FileOpen(@ScriptDir & "\downloadAndSave.log", 1)
$result = 1
$txt = ""
Sleep(200)
$activeWindow = WinWait("[TITLE:Opening; CLASS:MozillaDialogClass]", "", 3)
WinActivate($activeWindow)
$activeWindowTitle = WinGetTitle($activeWindow)
$flash = WinFlash($activeWindowTitle, "", 2, 50) ; Just to Flash the window
_FileWriteLog($hFile, "ActiveWindowTitle1='" & $activeWindowTitle & "'")
_FileWriteLog($hFile, "$flash1='" & $flash & "'")
If WinExists($activeWindow) Then
   Sleep(300)
   Send("!s")
   Sleep(100)
   Send("{ENTER}")
   Sleep(100)
   $activeWindow = WinWait("[TITLE:Enter; CLASS:#32770]", "", 3)
   WinActivate($activeWindow)
   Sleep(200)
   If NOT WinExists($activeWindow) Then
       $flash = WinFlash($activeWindowTitle, "", 2, 50) ; Just to Flash the window
       _FileWriteLog($hFile, "$flash2='" & $flash & "'")
       Sleep(300)
       Send("!s")
       Sleep(100)
       Send("{ENTER}")
       Sleep(200)
       $activeWindow = WinWait("[TITLE:Enter; CLASS:#32770]", "", 3)
       WinActivate($activeWindow)
       Sleep(200)
   EndIf
   $activeWindowTitle = WinGetTitle($activeWindow)
   _FileWriteLog($hFile, "ActiveWindowTitle2='" & $activeWindowTitle & "'")
   _FileWriteLog($hFile, "$activeWindow2='" & $activeWindow & "'")
   WinFlash($activeWindowTitle, "", 2, 50) ; Just to Flash the window
   $txt = ControlGetText($activeWindowTitle, "", "[CLASS:Edit; INSTANCE:1]")
   _FileWriteLog($hFile, "txt='" & $txt & "'")
   $allText = ControlSetText($activeWindowTitle, "", "Edit1", @ScriptDir &"\"& $txt)
   _FileWriteLog($hFile, "deleting file: '" & @ScriptDir &"\"& $txt &"'")
   FileDelete(@ScriptDir &"\"& $txt)
   $click = ControlClick($activeWindowTitle, "", "[CLASS:Button; INSTANCE:1]")
   $close = WinWaitClose($activeWindowTitle, "", 3)
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
	  WinClose("[TITLE:Enter; CLASS:#32770]", "")
	  WinClose("[TITLE:Opening; CLASS:MozillaDialogClass]", "")
	  _FileWriteLog($hFile, "nu este bine='" & $close & "'")
	  $result = 1
   EndIf
EndIf
_FileWriteLog($hFile, "$result='" & $result & "'")
_FileWriteLog($hFile, "------------------------- NewDownloadAndSave --------------------------------")
FileClose($hFile)
Exit ($result)