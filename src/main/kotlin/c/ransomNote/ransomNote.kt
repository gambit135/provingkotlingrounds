package c.ransomNote

import java.util.*

// Complete the checkMagazine function below.
fun checkMagazine(magazine: Array<String>, note: Array<String>): Unit {
    //println("Magazine is: " + Arrays.toString(magazine))
    //println("Note is: " + Arrays.toString(note))

    //map1 has magazine words and # of occurrences
    //map2 has note words and # of occurrences

    //once done, iterate through each of the note's words/keys,
    //and get them on the magazine's map as well. If it returns either null or
    //a number smaller than the one on the note's map, return NO, else return yes

    val magazineMap: HashMap<String, Int> = HashMap(magazine.size
            , 0.75f)
    val noteMap: HashMap<String, Int> = HashMap(note.size, 0.75f)

    for (word in magazine) {
        var counter = magazineMap[word] ?: 0
        counter++
        magazineMap[word] = counter
    }
    for (word in note) {
        val magazineCounter = magazineMap[word] ?: 0
        //If we know that the word isn't on the magazine already, dismiss
        if (magazineCounter == 0) {
            println("No")
            return
        }
        var noteCounter = noteMap[word] ?: 0
        noteCounter++
        noteMap[word] = noteCounter
    }
    //at this point, every word in note is contained in the magazine,
    // so not nulls shall be returned by calling on magazine with keys from note
    for (key in noteMap.keys) {
        //println("Key: $key, noteMap[key]: $noteMap[key], magazineMap[key]: $magazineMap[key]")
        //println("Key: $key")
        //println("magazineValue: " + magazineMap[key])
        //println("noteValue: " + noteMap[key])

        val noteValue = noteMap[key] ?:0
        val magValue = magazineMap[key]?:0
        //If there are more notes on the note than on the magazine, won't be enough
        if(noteValue > magValue){
            //println("key: $noteValue > $magValue")
            println("No")
            return
        }
        else{
            //println("$key: $noteValue !> $magValue")
        }
    }
    println("Yes")
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val mn = scan.nextLine().split(" ")

    val m = mn[0].trim().toInt()

    val n = mn[1].trim().toInt()

    val magazine = scan.nextLine().split(" ").toTypedArray()

    val note = scan.nextLine().split(" ").toTypedArray()

    checkMagazine(magazine, note)
}