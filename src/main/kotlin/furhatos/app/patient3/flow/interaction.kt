package furhatos.app.patienmediumt3.flow


import furhatos.app.patient3.flow.Interaction
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import java.util.*
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.patientmedium3.nlu.*
import java.util.*


val Start : State = state(Interaction) {
    onEntry {
        furhat.say("Welcome to this module, in which you will learn about the third part of establishing a conscious " +
                " therapeutic alliance. This involves getting the patient to declare a specific instance in which their issue " +
                " manifests in their life. In order to get a better understanding of what the patient is struggling " +
                " with, it is helpful to have a concrete example of how their issue affects their life.")
        furhat.say(" By having a particular instance when their issue manifests, the therapist can gain greater " +
                " insight into the root of the problem, as well as finding ways of dealing with it.")
        furhat.ask(" In order to to complete the module, you will have to block five defenses successfully, getting  " +
                " the patient to declare a specific instance of the problem. If you are ready say continue," +
                " if you would like to hear the instructions again say repeat.")
    }

    onResponse<Continue> {
        goto(DeclareSpecific)
    }

    onResponse<Repeat> {
        goto(StartGoto)
    }
}

val StartGoto : State = state {
    onEntry {
        goto(Start)
    }
}



val Wait3 : State = state {

    onTime(delay=1500) {
        furhat.ask("When you are ready for the next defense, say next")
    }

    onResponse<Continue> {
        goto(Counter3)
    }
}


val DeclareSpecific : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> goto(Generalization3)
            1 -> goto(NoMemory3)
            2 -> goto(Diversification3)
            3 -> goto(Avoidance3)
            4 -> goto(Intellectualization3)
        }
    }
}



var counter3 = 0

val Counter3 : State = state {

    onEntry {
        counter3 += 1
        if (counter3 < 5)
            goto(DeclareSpecific)
        else {
            furhat.say(" There was a talk last weekend when I got angry with my dad right after he mentioned my " +
                    "mother's illness.")
            goto(Resolution3)
        }
    }
}



val Generalization3Goto : State = state {
    onEntry {
        goto(Generalization3)
    }
}



val Generalization3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.ask(" It's the same pattern every time we talk on the phone")
            1 -> furhat.ask(" I just feel overall frustrated whenever it happens and don't know what to do")
            2 -> furhat.ask(" It's just this sense of annoyance and it keeps coming up in many instances")
            3 -> furhat.ask(" I can't give a specific example because it's a bigger issue, I think it's got " +
                    "to do with my overall character")
            4 -> furhat.ask(" I have this tendency in those situations to flare up instead of reacting calmly")
        }
    }

    onResponse<GeneralizationBlock3> {

        it.intent.feel
        it.intent.avoid
        it.intent.person
        it.intent.general
        it.intent.notice
        it.intent.specific
        it.intent.problem
        it.intent.specific

       furhat.say("Correct")

        goto(Counter3)
    }



    onResponse<NoMemoryBlock3> {
        furhat.say(" Incorrect")
        goto(Generalization3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Incorrect")
        goto(Generalization3Goto)
    }

    onResponse<AvoidanceBlock3> {
        furhat.say(" Incorrect")
        goto(Generalization3Goto)
    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say("Incorrect")
        goto(Generalization3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense. ")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" That was generalization. Notice how the patient is presenting a large or general account of the" +
                " issue, using words like 'pattern', 'usually', or 'overall', instead of providing a specific instance of " +
                " their problem")
        goto(Wait3)
    }

    onResponse<Hint> {
        furhat.say(" Is the patient being specific about their response? Notice how instead of giving a particular " +
                " instance, they turn it into something larger. What defense mechanism follows this pattern?")
        goto(Generalization3Goto)
    }

}

val NoMemory3Goto : State = state {
    onEntry {
        goto(NoMemory3)
    }
}


val NoMemory3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.ask(" I can't really remember a particular time it happened")
            1 -> furhat.ask(" How am I supposed to remember all the details that happen in my life")
            2 -> furhat.ask(" Thinking about it I can't really recall a specific episode when I got angry")
            3 -> furhat.ask(" I just have a vague sense of what happened, it's not very clear in my memory")
            4 -> furhat.ask(" I just know it happened sometime last weekend but can not remember more than that")
        }
    }

    onResponse<NoMemoryBlock3> {

        it.intent.general
        it.intent.avoid
        it.intent.memory
        it.intent.notice
        it.intent.problem
        it.intent.specific

        furhat.say("Correct")

        goto(Counter3)
    }


    onResponse<GeneralizationBlock3> {
        furhat.say(" Incorrect")
        goto(NoMemory3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Incorrect")
        goto(NoMemory3Goto)
    }

    onResponse<AvoidanceBlock3> {
        furhat.say(" Incorrect ")
        goto(NoMemory3Goto)
    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say(" Incorrect ")
        goto(NoMemory3Goto)
    }

    onResponse<TryAgain> {
        furhat.say("Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This is a case of a no memory response. Notice how the patient avoids describing an instance " +
                "of their problem by saying they cannot remember one way or another. Of course patients may genuinely " +
                " not remember certain things, but if they consistently cannot recall emotionally important events, it" +
                " may be a hint they are using it as a defense mechanism.")
        goto(DeclareSpecific)
    }

    onResponse<Hint> {
        furhat.say(" What excuse is the patient using to not describe a particular instance of their problem? If " +
                " you listen closely, the patient is being rather direct about why they fail to provide you with this information.")
        goto(NoMemory3Goto)
    }
}

val Diversification3Goto : State = state {

    onEntry {
        goto(Diversification3)
    }
}

val Diversification3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.ask(" Another thing that is bothering me is how the rest of the family backs up my dad")
            1 -> furhat.ask(" I just want to mention first how crazy the mall was yesterday with all those sales")
            2 -> furhat.ask(" Did I tell you about the traffic on my way here. That's why I was a bit late")
            3 -> furhat.ask(" Truth be told my dad is overall a good guy. This one time he really helped me out " +
                    "when I was in financial trouble")
            4 -> furhat.ask(" You make me feel like I do when my wife complains I don't do the dishes. Though " +
                    "she does have a point sometimes.")
        }
    }

    onResponse<DiversificationBlock3> {

        furhat.say("Correct")

        goto(Counter3)

    }

    onResponse<GeneralizationBlock3> {
        furhat.say(" Incorrect")
        goto(Diversification3Goto)
    }

    onResponse<NoMemoryBlock3> {
        furhat.say(" Incorrect")
        goto(Diversification3Goto)
    }

    onResponse<AvoidanceBlock3> {
        furhat.say(" Incorrect")
        goto(Diversification3Goto)
    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say(" Incorrect")
        goto(Diversification3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This defense is diversification, which means the patient changes topics in order to avoid " +
                " answering the question. You can usually tell it's diversification if the patient suddenly introduces " +
                " something unrelated into the conversation")
        goto(Wait3)
    }

    onResponse<Hint> {
        furhat.say(" The patient is reaching quite far to avoid mentioning a specific instance. What are they talking" +
                " about instead? Is it related at all to the topic at hand?")
        goto(Diversification3Goto)
    }


}


val Avoidance3Goto : State = state {
    onEntry {
        goto(Avoidance3)
    }
}


val Avoidance3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.say(" I really don't want to think about that, it's very painful")
            1 -> furhat.say(" I haven't thought of any incidents for a long time, and don't intend to think about " +
                    " it now or again.")
            2 -> furhat.say("You are asking me to dive into my feelings? That's not what I want, I thought therapy " +
                    "was supposed to make me happy and not go into negative emotions.")
            3 -> furhat.say(" I'd really prefer not to think about it, it causes so many negative feelings that " +
                    " I try to avoid.")
            4 -> furhat.say(" I have been doing a good job of avoiding these thoughts and feelings, and don't see " +
                    " a reason to go into them again.")
        }
    }

    onResponse<AvoidanceBlock3> {

        it.intent.avoid
        it.intent.feel

        furhat.say("Correct")

        goto(Counter3)
    }

    onResponse<GeneralizationBlock3> {
        furhat.say("Incorrect")
        goto(Avoidance3Goto)
    }

    onResponse<NoMemoryBlock3> {
        furhat.say(" Incorrect")
        goto(Avoidance3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Incorrect")
        goto(Avoidance3Goto)
    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say("Incorrect")
        goto(Avoidance3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This defense is avoidance. Though all defenses are ultimately a form of avoidance, in these cases" +
                " the patients are consciously aware that they are avoiding their problem. It is helpful to remind them" +
                " how this strategy has not served them in the past, and that they need to confront their emotions in " +
                " order to improve. ")
        goto(DeclareSpecific)
    }

    onResponse<Hint> {
        furhat.say( " Notice that the patient seems rather aware of how they are evading their feelings and do it" +
                " quite consciously. This is distinctly different from other defense mechanisms, in which patients are " +
                " often unaware of their pattern. ")
        goto(Avoidance3Goto)
    }
}

val Intellectualization3Goto : State = state {
    onEntry {
        goto(Intellectualization3)
    }
}

val Intellectualization3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.say(" Last time it happened, I remember thinking how we really need to find a solution" +
                    " to this problem")
            1 -> furhat.say(" Sometimes when it happens, I try to use techniques from books about how to calm down, " +
                    " but it does not really work in the moment, you know. ")
            2 -> furhat.say(" Maybe if I had more emotional intelligence I would be able to handle those types" +
                    " of situations better, but I suppose I have to deal with what I have.")
            3 -> furhat.say( " It happened one time last weekend, and I remember being really on edge that day. And" +
                    " when I'm on edge I am less able to deal with confrontation. So there is that.")
            4 -> furhat.say(" A specific situation you say? How is that supposed to help? I mean, I have read " +
                    " that I need to deal with my childhood issues in order to solve problems like these.")
        }

    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say("Correct")
        goto(Counter3)
    }

    onResponse<RationalizationBlock1> {
        furhat.say(" Incorrect")
        goto(Intellectualization3Goto)
    }

    onResponse<GeneralizationBlock3> {
        furhat.say(" Incorrect")
        goto(Intellectualization3Goto)
    }

    onResponse<NoMemoryBlock3> {
        furhat.say(" Incorrect")
        goto(Intellectualization3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Incorrect")
        goto(Intellectualization3Goto)
    }

    onResponse<AvoidanceBlock3> {
        furhat.say( " Incorrect")
        goto(Intellectualization3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This defense is intellectualization. In intellecualization the patient offers their thoughts" +
                " instead of their emotions. Notice how when asked about a specific scenario made them feel, they instead " +
                " mention their ideas and thoughts about it.Whenever patients mention ideas over feelings it is a good " +
                " sign they may be intellecutalizing.")
        goto(DeclareSpecific)
    }

    onResponse<Hint> {
        furhat.say(" Consider what kind of information the patient is giving you instead of describing a specific" +
                " instance of the problem, and how it made them feel. Are they centered in emotions or are they providing " +
                " you with another kind of information?")
        goto(Intellectualization3Goto)
    }
}






val Resolution3 : State = state {

    onEntry {
        furhat.say("Great! You helped patient to declare a specific instance in which their problem manifested. " +
                " Turns out they got angry with their father in relation to talking about their mother's illness. This" +
                " is exactly the type of valuable information that describing a particular instance may yield, and helps" +
                " progress the therapeutic process. How exactly to do this will be the topic of future modules")
    }
}


