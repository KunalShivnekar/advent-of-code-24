fun main() {
    fun createMap(input: List<String>): Map<String, List<String>> {
        val res = mutableMapOf<String, MutableList<String>>()
        input.forEach { i ->
            val (x, y) = """([a-zA-Z]+)-([a-zA-Z]+)""".toRegex().find(i)!!.destructured
            res.getOrPut(x) { mutableListOf() }.add(y)
            res.getOrPut(y) { mutableListOf() }.add(x)
        }
        return res
    }

    fun part1(input: List<String>): Int {
        val map = createMap(input)
        val visited = mutableMapOf<String, Boolean>()
        val teams = mutableSetOf<List<String>>()
        map.keys.forEach { first ->
            val counted = mutableMapOf<String, Boolean>()
            map[first]!!.forEach { second ->
                if (visited.getOrDefault(second, false).not()) {
                    map[second]!!.intersect(map[first]!!.toSet()).toList().takeIf { it.isEmpty().not() }?.forEach {
                        if (counted.getOrDefault(it, false).not()) {
                            val t = mutableListOf(first, second, it)
                            t.sort()
                            teams.add(t)
                        }
                    }
                    counted[second] = true
                }
            }
            visited[first] = true
        }
        var res = 0
        teams.forEach { team ->
            for (node in team) {
                if (node.startsWith("t")) {
                    res++
                    break
                }
            }
        }
        return res
    }

    fun part2(input: List<String>): Int {
        return input.size
    }


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
