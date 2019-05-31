package furhatos.app.patient3

import furhatos.app.patient3.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class Patient3Skill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
