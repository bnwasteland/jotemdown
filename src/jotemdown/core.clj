(ns jotemdown.core
  (:require [instaparse.core :as insta]))

(def parse-jotdown
  (insta/parser
    "<Blocks> = (Paragraph)+
     Paragraph = Text Blanklines
     <Blanklines> = EOL (Whitespace* EOL)+
     <Text> = (Span / SpanDelimiter)+
     <Span> = (Emphasis / CodeSpan / PlainText)
     Emphasis = <'*'> Span+ <'*'>
     CodeSpan = <'`'> Span+ <'`'>
     <PlainText> = (EscapedSpanDelimiter / UndecoratedString / UndecoratedString '\\n')+
     <EscapedSpanDelimiter> = <'\\\\'> SpanDelimiter
     <SpanDelimiter> = '*' | '`'
     <UndecoratedString> = #'[^*`\\\\\\n]+'
     <Whitespace> = <#'(\\ | \\t)+'>
     <EOL> = <'\\n'> | EOF
     <EOF> = <#'\\Z'>"))
