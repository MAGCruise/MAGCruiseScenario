(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'ChineseFacilitator 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'JapaneseDiscussant1 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'ChineseDiscussant1 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'JapaneseDiscussant2 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'ChineseDiscussant2 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'JapaneseDiscussant3 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'ChineseDiscussant3 'human org.magcruise.gaming.examples.discussion.actor.Talker)))
