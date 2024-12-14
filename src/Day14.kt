fun main() {

    fun finalPos(limit: Int, velocity: Int, initialPos: Int): Int = ((velocity * 100) + initialPos).mod(limit)

    fun part1(input: List<String>): Int {
        val xLimit = 101
        val yLimit = 103
        return input.map { i ->
            val (x, y, xV, yV) = """p=([-\d]+),([-\d]+) v=([-\d]+),([-\d]+)""".toRegex().find(i)!!.destructured
            val xFinal = finalPos(xLimit, xV.toInt(), x.toInt())
            val yFinal = finalPos(yLimit, yV.toInt(), y.toInt())
            Pair(xFinal, yFinal)
        }.run {
            listOf(
                count { pair -> pair.first < xLimit / 2 && pair.second < yLimit / 2 },
                count { pair -> pair.first > xLimit / 2 && pair.second < yLimit / 2 },
                count { pair -> pair.first < xLimit / 2 && pair.second > yLimit / 2 },
                count { pair -> pair.first > xLimit / 2 && pair.second > yLimit / 2 }
            ).reduce(Int::times)
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    part1(input).println()
    //part2(input).println()
}
