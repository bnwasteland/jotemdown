(ns jotemdown.core
  (:require [instaparse.core :as insta]))

(def parse-jotdown
  (insta/parser
    "<Blocks> = (Paragraph)+
     Paragraph = Text Blanklines
     <Blanklines> = EOL (Whitespace* EOL)+
     <Text> = (Span / SpanDelimiter)+
     <Span> = Emphasis / CodeSpan / Link / PlainText
     Emphasis = <'*'> Emphasized <'*'>
     CodeSpan = <'`'> Code <'`'>
     Link = <'['> Label <']'> Whitespace* <'['> Target <']'>
     <Emphasized> = (Span / '`')+
     <Code> = (Span / '*')+
     Label = (Span / '*' / '`')+
     Target = UrlString
     <PlainText> = (EscapedSpanDelimiter / UndecoratedString / UndecoratedString '\\n')+
     <EscapedSpanDelimiter> = <'\\\\'> SpanDelimiter
     <SpanDelimiter> = '*' | '`' | '[' | ']'
     <UrlString> = #'[^\\]\\n]+'
     <UndecoratedString> = #'[^*`\\[\\]\\\\\\n]+'
     <Whitespace> = <#'(\\ | \\t)+'>
     <EOL> = <'\\n'> | EOF
     <EOF> = <#'\\Z'>"))
