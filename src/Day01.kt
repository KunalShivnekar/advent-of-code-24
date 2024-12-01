import java.util.regex.Pattern
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        var distance = 0
        input.forEach { line ->
            line.split(Pattern.compile("\\s+")).apply {
                list1.add(get(0).toInt())
                list2.add(get(1).toInt())
            }
        }
        list1.sort()
        list2.sort()
        for (i in input.indices){
            distance += (list1[i] - list2[i]).absoluteValue
        }
        return distance
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 900)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}