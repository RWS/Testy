WinWait($CmdLine[2], "", 3)
WinFlash($CmdLine[2], "", 1, 50) ; Just to Flash the window
;ControlSetText($CmdLine[2], "", "[CLASS:Edit; INSTANCE:1]", "D:\Mill\src\test\resources\file\export.xml")
ControlSetText($CmdLine[2], "", "[CLASS:Edit; INSTANCE:1]", $CmdLine[1])
Sleep(300)
ControlClick($CmdLine[2], "", "[CLASS:Button; INSTANCE:1]")
WinWaitNotActive($CmdLine[2], "", 3)