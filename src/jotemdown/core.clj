(ns jotemdown.core
  (:require [instaparse.core :as insta]))

(def parse-md
  (insta/parser
    "<Blocks> = (Paragraph | Header | List | Ordered | Code | Rule)+
    Header = Line Headerline Blankline+
    <Headerline> = h1 | h2
    h1 = '='+
    h2 = '-'+
    List = Listline+ Blankline+
    Listline = Listmarker Whitespace+ Word (Whitespace Word)* EOL
    <Listmarker> = <'+' | '*' | '-'>
    Ordered = Orderedline+ Blankline+
    Orderedline = Orderedmarker Whitespace* Word (Whitespace Word)* EOL
    <Orderedmarker> = <#'[0-9]+\\.'>
    Code = Codeline+ Blankline+
    Codeline = <Space Space Space Space> (Whitespace | Word)* EOL
    Rule = Ruleline Blankline+
    <Ruleline> = <'+'+ | '*'+ | '-'+>
    Paragraph = Line+ Blankline+
    <Blankline> = Whitespace* EOL
    <Line> = Linepre Word (Whitespace Word)* Linepost EOL
    <Linepre> = (Space (Space (Space)? )? )?
    <Linepost> = Space?
    <Whitespace> = #'(\\ | \\t)+'
    <Space> = ' '
    <Word> = #'\\S+'
    <EOL> = <'\\n'>"))

(def parse-jotdown
  (insta/parser
    "<Blocks> = (Paragraph)+
     Paragraph = Text Blanklines
     <Blanklines> = EOL (Whitespace* EOL)+
     <Text> = (PlainText '\\n' | PlainText)+
     <PlainText> = #'(\\ |\\t|\\S)+'
     <Whitespace> = <#'(\\ | \\t)+'>
     <EOL> = <'\\n'> | EOF
     <EOF> = <#'\\Z'>"))
