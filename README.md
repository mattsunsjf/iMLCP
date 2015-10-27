# iMLCP (Interactive MLCP Shell)

iMLCP is an interactive shell for MarkLogic Content Pump.

Author: mattsun

## Dependency

* [jlineEnhanced](https://github.com/mattsunsjf/jline2-imlcp) A friendly fork of jline2 with specific enhancement for iMLCP 
* [MLCP](http://developer.marklogic.com/products/mlcp) MarkLogic Content Pump, an open-source, Java-based command-line tool that provides the fastest way to import, export, and copy data to or from MarkLogic databases 

## Features

* Run any command of MLCP. Use iMLCP same way as mlcp.sh if you are already familiar with it
* Interactive (Read-Eval-Print Loop)...
* Auto-completion: MLCP command, options, frequently-used value (true, false, xml, ...) and system path
* Command history (hit up arrow)
* Run any system shell command
* Easy to deploy
* Get rid of many existing system shell limitations, include but not limit to:
  * No automatic path expanding limitation of system shell (which may cause 'Argument too long' error)
  * No need to use double quote to enclose some fields (Eg. -delimiter "|", since vertical bar has special meaning in system shell)
  * A easy replacement of options_file in some cases (Eg. use tab as delimiter and contains space in csv header)

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


# (GitHub-Flavored) Markdown Editor

Basic useful feature list:

 * Ctrl+S / Cmd+S to save the file
 * Ctrl+Shift+S / Cmd+Shift+S to choose to save as Markdown or HTML
 * Drag and drop a file into here to load it
 * File contents are saved in the URL so you can share files


I'm no good at writing sample / filler text, so go write something yourself.

Look, a list!

 * foo
 * bar
 * baz

And here's some code! :+1:

```javascript
$(function(){
  $('div').html('I am a div.');
});
```

This is [on GitHub](https://github.com/jbt/markdown-editor) so let me know if I've b0rked it somewhere.


Props to Mr. Doob and his [code editor](http://mrdoob.com/projects/code-editor/), from which
the inspiration to this, and some handy implementation hints, came.

### Stuff used to make this:

 * [markdown-it](https://github.com/markdown-it/markdown-it) for Markdown parsing
 * [CodeMirror](http://codemirror.net/) for the awesome syntax-highlighted editor
 * [highlight.js](http://softwaremaniacs.org/soft/highlight/en/) for syntax highlighting in output code blocks
 * [js-deflate](https://github.com/dankogai/js-deflate) for gzipping of data to make it fit in URLs

