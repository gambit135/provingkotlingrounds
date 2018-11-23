package c.balancedBrackets

import java.util.*
import kotlin.collections.HashMap

val mapOfClosures:HashMap<Char, Char> = HashMap()
val setOfOpenings = setOf('{', '[', '(')
val setOfClosings = setOf('}', ']', ')')

fun main(args: Array<String>) {

    mapOfClosures['{'] = '}'
    mapOfClosures['['] = ']'
    mapOfClosures['('] = ')'
    mapOfClosures[')'] = '('
    mapOfClosures[']'] = '['
    mapOfClosures['}'] = '{'

    val scan = Scanner(System.`in`)
    val t = scan.nextLine().trim().toInt()
    for (tItr in 1..t) {
        val expression = scan.nextLine()
        isBalanced(expression)
    }
}

fun isBalanced(string: String) {
    var stack = mutableListOf<Char>()
    for (char in string) {
        if (mapOfClosures.keys.contains(char)) {
            if (setOfOpenings.contains(char)) {
                stack.add(char)
                println("Stack is: " + stack)
                continue
            }
            if (setOfClosings.contains(char)) {
                val topOfStack = stack.last()

                println("pop " + topOfStack + " because " + char)
                //verify if the corresponding opening is in the top of stack
                if (mapOfClosures[char] == topOfStack) {
                    //Pop and continue
                    stack = stack.dropLast(1).toMutableList()
                    println("stack is: " + stack)
                } else {
                    println("NO")
                    return
                }
            }
            println("YES")
            return
        }
        else{
            println("NO")
            return
        }
    }
}

