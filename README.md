# iMLCP (Interactive MLCP Shell)

iMLCP is an interactive shell for MarkLogic Content Pump.

Author: [Matt Sun](mailto:matt.sun@marklogic.com)


## Features

* Run any MLCP command as-is
* Interactive (Read-Eval-Print Loop)...
* Auto-completion: MLCP command, options, frequently-used value (true, false, xml, ...) and system path
* Command history (hit up arrow)
* Run any system shell command
* **Wildcard** file path
* Easy to deploy, standalone project of MLCP
* Get rid of many existing system shell limitations, include but not limit to:
  * **Use space** in your option argument! (-output_uri_replace "str,'hello world'" doesn't work before becasue space between hello and world)
  * **No automatic path expanding limitation** of system shell (which may cause '*Argument too long*' error)
  * **Use | pipline as delimiter!** or any other special character... No need to use double quote to enclose some fields (-delimiter | deosn't work before since vertical bar has special meaning in system shell)


## Command Documentation

| Command      | Usage                                                  |
| -------------| ------------------------------------------------------ |
| cls/clear    |Clean the screen                                        |
| quit/exit    | Exit the MLCP interactive shell                        |
| $[command]   | Execute system shell command. Example: $ls -al         |
| help         |Help for MLCP                                           |
| CTRL+C       |Stop the MLCP job or discard current command line input |
| ?            |Help for interactive shell                              |

All the commands are case-insensitive (except system shell commands).


## Usage

* Copy all the file(s) in ${rootdir}/deliverable/lib to ${mlcp}/lib
* Copy all the file(s) in ${rootdir}/deliverable/bin to ${mlcp}/bin
* from MLCP root directory, run following command:
``` bash
bin/imlcp.sh
```
Then iMLCP should be lauched.

## Build

From root directory of the project, run following command:
``` bash
sh build.sh
```
After successful build, follow the steps in #Usage to run iMLCP.

### System Requirement
* Maven 3 (to build)
* Java 7 (same version requirement of MLCP)

### Dependency

* [jlineEnhanced v2.15](https://github.com/mattsunsjf/jline2-imlcp) A friendly fork of jline2 with specific enhancement for iMLCP 
* [MLCP v8.0-3](http://developer.marklogic.com/products/mlcp) MarkLogic Content Pump, an open-source, Java-based command-line tool that provides the fastest way to import, export, and copy data to or from MarkLogic databases
* Other dependencies are mostly for MLCP, see pom.xml for more details.

## Platform

Currently only support *nix systems. Will extend the support to Windows and other system later.

## Versioning

iMLCP uses the same version of MarkLogic Server, which is different from MCLP. Currently only a few versions are available (see branches), but a branch matching MarkLogic Server version can be made on demand. Email [author](mailto:matt.sun@marklogic.com) for more details.

## Todo

- Option value masking (password)
- Windows support
- Shell output coloring
- User experience improvement

## Quick Demo (Video)

[![ScreenShot](http://tekloaded.com/wp-content/uploads/2015/02/youtube-vid.jpg)](http://youtu.be/CTxOJuo-Ju8)

## License

In place of a legal notice, here is a blessing:

	May you do good and not evail.
    May you find forgiveness for yourself and forgive others.
    May you share freely, never taking more than you give.
