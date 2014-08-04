(ns jotemdown.core-test
  (:require [clojure.test :refer :all]
            [jotemdown.core :refer :all]))

(defmacro parses-to [string & expected]
  `(is (= (parse-jotdown ~string) (list ~@expected))))

(deftest parsing-test
  (testing "Simple Markdown:"
    (testing "paragraphs"
      (parses-to "Paragraph Text."
                 [:Paragraph "Paragraph Text."])

      (parses-to "Paragraph Text.\n"
                 [:Paragraph "Paragraph Text."])

      (parses-to "Paragraph Text.\n\n"
                 [:Paragraph "Paragraph Text."])

      (parses-to "Paragraph Text.\nSame Paragraph."
                 [:Paragraph "Paragraph Text." "\n" "Same Paragraph."])

      (parses-to "Paragraph Text.\nSame Paragraph.\n"
                 [:Paragraph "Paragraph Text." "\n" "Same Paragraph."])

      (parses-to "Paragraph Text.\nSame Paragraph.\n\n"
                 [:Paragraph "Paragraph Text." "\n" "Same Paragraph."])

      (parses-to "Paragraph Text.\nSame Paragraph.\nSame Paragraph Again."
                 [:Paragraph "Paragraph Text." "\n"
                             "Same Paragraph." "\n"
                             "Same Paragraph Again."])

      (parses-to "Paragraph Text.\nSame Paragraph.\n Same Paragraph Again.\n And Again."
                 [:Paragraph "Paragraph Text." "\n"
                             "Same Paragraph." "\n"
                             " Same Paragraph Again." "\n"
                             " And Again."])

      (parses-to "Paragraph Text.\n\nDifferent Paragraph."
                 [:Paragraph "Paragraph Text."]
                 [:Paragraph "Different Paragraph."])

      (parses-to "Paragraph Text.\n\nDifferent Paragraph.\n"
                 [:Paragraph "Paragraph Text."]
                 [:Paragraph "Different Paragraph."])

      (parses-to "Paragraph Text.\n \nDifferent Paragraph.\n\n"
                 [:Paragraph "Paragraph Text."]
                 [:Paragraph "Different Paragraph."])

      (parses-to "Paragraph Text.\n Same Paragraph.\n\nDifferent Paragraph."
                 [:Paragraph "Paragraph Text." "\n" " Same Paragraph."]
                 [:Paragraph "Different Paragraph."])

      (parses-to "Paragraph Text.\nSame Paragraph.\n\nDifferent Paragraph.\n"
                 [:Paragraph "Paragraph Text." "\n" "Same Paragraph."]
                 [:Paragraph "Different Paragraph."])

      (parses-to "Paragraph Text.\nSame Paragraph.\n\nDifferent Paragraph.\n\n"
                 [:Paragraph "Paragraph Text." "\n" "Same Paragraph."]
                 [:Paragraph "Different Paragraph."])

      (parses-to "Paragraph Text.\n\n\n\n\nDifferent Paragraph."
                 [:Paragraph "Paragraph Text."]
                 [:Paragraph "Different Paragraph."]))

    (testing "spans"
      (parses-to "Blah *blah* blah" [:Paragraph "Blah " [:Emphasis "blah"] " blah"])

      (parses-to "Blah ` blah` blah" [:Paragraph "Blah " [:CodeSpan " blah"] " blah"])

      (parses-to "Blah *blah * blah" [:Paragraph "Blah " [:Emphasis "blah "] " blah"])

      (parses-to "Blah ` blah ` blah" [:Paragraph "Blah " [:CodeSpan " blah "] " blah"])

      (parses-to "Blah *blah blah*" [:Paragraph "Blah " [:Emphasis "blah blah"]])

      (parses-to "`Blah blah` blah" [:Paragraph [:CodeSpan "Blah blah"] " blah"])

      (parses-to "*Blah blah blah*" [:Paragraph [:Emphasis "Blah blah blah"]])

      (parses-to "`Blah\nblah blah`" [:Paragraph [:CodeSpan "Blah" "\n" "blah blah"]])

      (parses-to "*Blah\n\nblah blah*"
                 [:Paragraph "*" "Blah"]
                 [:Paragraph "blah blah" "*"])

      (parses-to "x*3" [:Paragraph "x" "*" "3"])

      (parses-to "*x*3*" [:Paragraph [:Emphasis "x"] "3" "*"])

      (parses-to "\\*x*3*" [:Paragraph "*" "x" [:Emphasis "3"]])

      (parses-to "*x\\*3*" [:Paragraph [:Emphasis "x" "*" "3"]])

      (parses-to "*x*3\\*" [:Paragraph [:Emphasis "x"] "3" "*"])

      (parses-to "*x*, *y*" [:Paragraph [:Emphasis "x"] ", " [:Emphasis "y"]]))))
