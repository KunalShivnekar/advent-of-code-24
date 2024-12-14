fun main() {
    val xLimit = 101
    val yLimit = 103

    fun moveBot(bot: Pair<Pair<Int, Int>, Pair<Int, Int>>, times: Int = 1) = Pair(
        Pair((bot.first.first + (bot.second.first * times)).mod(xLimit), (bot.first.second + (bot.second.second * times)).mod(yLimit)),
        Pair(bot.second.first, bot.second.second)
    )

    fun bots(input: List<String>) = input.map { i ->
        val (x, y, xV, yV) = """p=([-\d]+),([-\d]+) v=([-\d]+),([-\d]+)""".toRegex().find(i)!!.destructured
        Pair(Pair(x.toInt(), y.toInt()), Pair(xV.toInt(), yV.toInt()))
    }

    fun part1(input: List<String>): Int {
        return bots(input).map { bot ->
            moveBot(bot, 100)
        }.run {
            listOf(
                count { pair -> pair.first.first < xLimit / 2 && pair.first.second < yLimit / 2 },
                count { pair -> pair.first.first > xLimit / 2 && pair.first.second < yLimit / 2 },
                count { pair -> pair.first.first < xLimit / 2 && pair.first.second > yLimit / 2 },
                count { pair -> pair.first.first > xLimit / 2 && pair.first.second > yLimit / 2 }
            ).reduce(Int::times)
        }
    }

    fun part2(input: List<String>): Int {
        var bots = bots(input)
        var second = 0
        while (second++ < xLimit * yLimit) {
            val set = hashSetOf<Pair<Int, Int>>()
            bots = bots.map { bot ->
                val new = moveBot(bot)
                set.add(Pair(new.first.first, new.first.second))
                new
            }
            if (set.size == bots.size) break
        }
        return second
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
