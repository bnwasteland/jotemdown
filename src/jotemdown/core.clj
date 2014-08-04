(ns jotemdown.core
  (:require [instaparse.core :as insta]))

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
