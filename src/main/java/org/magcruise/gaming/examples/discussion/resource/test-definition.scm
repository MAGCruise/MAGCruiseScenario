(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'Player1 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'Player2 'human org.magcruise.gaming.examples.discussion.actor.Talker)))


(define (def:after-setup-game-builder builder ::GameBuilder)
 (builder:addDefAssignmentRequests (list 'Player1 'Player2) (list 'user1 'user2))
  (builder:setDefKeyValueTables


  (def:key-value-table actor: 'Player1 table-name: 'auto-input
    (list name: 'input-msg default: "END" round-queues:
       (list
          (list "こんにちは．" "今日は良い天気ですね．" "何の話しをしますか？" "朝ご飯はカレーでした．" "さようなら．")
          (list "こんにちは．" "今日は良い天気ですね．" "何の話しをしますか？" "朝ご飯はカレーでした．" "さようなら．"))))


  (def:key-value-table actor: 'Player2 table-name: 'auto-input
                       (list name: 'input-msg default: "END" round-queues:
                             (list
                              (list "こんにちは．" "そうですね．" "そうですね．" "そうですね．" "さようなら．")
                              (list "こんにちは．" "そうですね．" "そうですね．" "そうですね．" "さようなら．"))))

))

