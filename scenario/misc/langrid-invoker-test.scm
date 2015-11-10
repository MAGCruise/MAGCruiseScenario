(define (def:game-scenario)
  (def:player 'LangridInvoker 'human SimplePlayer)
  (def:round 
    (def:stage 'langrid-test
      (def:task 'LangridInvoker 'langrid-test))))

(define (langrid-test context ::Context self ::Player)

    (manager:show-message 'all 
        (langrid:BilingualDictionary-search "kyoto1.langrid:Lsd" "en" "ja" "bank" "COMPLETE" ))

    (let ((original-sentence "今日は雨です．"))
        (manager:show-message 'all 
            (to-string "<br>原文は<br><strong>" original-sentence "</strong><br>です．"))
        (test-langrid-Translation original-sentence))


  (define (test-langrid-Translation original-sentence)
    (let* ((translated-sentence
              (langrid:Translation-translate "kyoto1.langrid:GoogleTranslate" "ja" "en" original-sentence))
           (back-translated-sentence
              (langrid:Translation-translate "kyoto1.langrid:GoogleTranslate" "en" "ja" translated-sentence)))
        (manager:show-message 'all 
            (to-string "<br>翻訳(日→英)の結果は<br><strong>" translated-sentence "</strong><br>です．"))
        (manager:show-message 'all 
            (to-string "<br>折り返し翻訳(日→英→日)の結果は<br><strong>" back-translated-sentence "</strong><br>です．")))))

