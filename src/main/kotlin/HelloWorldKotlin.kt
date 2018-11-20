import kotlin.math.max

fun main(args: Array<String>) {
    println(message = "Hello, Kotlin!")
    //println(maxNum(b = 1, a = 3))
    for(index in 0..10){
        println("index: $index" )
    }
    printSerializedUrl()
}

fun maxNum(a: Int, b: Int): Int {
    //return max(a,b)
    return if (a > b) a else b
}

fun printSerializedUrl() {
    val c = 500
    for (i in 0..c) {
        var sValue = i.toString()
        for (j in 2..3) {
            if (sValue.length < j) {
                sValue = "0$sValue"
            }
        }
        println(sValue)


    }
}

//fun populateArray():Array<Int>{
//int[][] states = new int[5][3];
//return Array(1, Int(12))
//}