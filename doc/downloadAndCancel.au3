#include <File.au3>
Local $hFile = FileOpen(@ScriptDir & "\download.log", 1)
$result = 1
WinWait($CmdLine[2], "", 1)
WinActivate($CmdLine[2], "")
WinFlash($CmdLine[2], "", 1, 50)
If (StringCompare("Opening", $CmdLine[2], 0) = 0) Then ;For FF
   $txt = WinGetTitle($CmdLine[2])
   $retxt = $CmdLine[2] & " " & $CmdLine[1]
   _FileWriteLog($hFile, "txt='" & $txt & "'")
   _FileWriteLog($hFile, "retxt='" & $retxt & "'")
   If (StringCompare($retxt, $txt, 0) = 0) Then
	  $result = 0
   EndIf
   WinClose($CmdLine[2], "")
   WinWaitNotActive($CmdLine[2], "", 2)
EndIf
FileClose($hFile)
Exit $result
