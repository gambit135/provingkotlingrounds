import kotlin.math.max

fun main(args: Array<String>){
    println(message = "Hello, Kotlin!")
    println(maxNum(b= 1,a =3))
}

fun maxNum(a: Int, b: Int): Int{
    //return max(a,b)
    return if (a > b) a else b
}