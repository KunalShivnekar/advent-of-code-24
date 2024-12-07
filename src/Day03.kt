fun main() {

    fun parseMultiplications(input: String): Long {
        val regex = """mul\((\d+),(\d+)\)""".toRegex()
        return regex.findAll(input).map {
            val (x, y) = it.destructured
            x.toLong() * y.toLong()
        }.sum()
    }

    fun clean(input: String): String {
        val firstDont = input.indexOf("don't()")
        if (firstDont == -1) return input
        var firstDo = input.indexOf("do()", startIndex = firstDont)
        if (firstDo == -1) firstDo = input.length
        return clean(input.take(firstDont) + input.drop(firstDo + 4))
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        input.forEach { line ->
            sum += parseMultiplications(line)
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        return parseMultiplications(clean(input.joinToString(separator = "")))
    }

    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
