(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'JapaneseDiscussant 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'ChineseDiscussant 'human org.magcruise.gaming.examples.discussion.actor.Talker)))
