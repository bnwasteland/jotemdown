(ns jotemdown.core
  (:require [instaparse.core :as insta]))

;; This method is from [Hitman][https://github.com/chameco/Hitman], but it
;; doesn't cover spans (those are done with post-processing), and it's very line oriented.
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
     <Text> = (Span / Span '\\n')+
     <Span> = (Emphasis / CodeSpan / PlainText / SpanDelimiter)
     Emphasis = <'*'> PlainText <'*'>
     CodeSpan = <'`'> PlainText <'`'>
     <PlainText> = (EscapedSpanDelimiter / UndecoratedString / UndecoratedString '\\n')+
     <EscapedSpanDelimiter> = <'\\\\'> SpanDelimiter
     <SpanDelimiter> = '*' | '`'
     <UndecoratedString> = #'[^*`\\\\\\n]+'
     <Whitespace> = <#'(\\ | \\t)+'>
     <EOL> = <'\\n'> | EOF
     <EOF> = <#'\\Z'>"))
