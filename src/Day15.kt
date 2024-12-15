fun main() {

    fun warehouseMap(input: List<String>): List<MutableList<Char>> = input
        .filter {
            it.startsWith("#")
        }.map {
            it.toCharArray().toMutableList()
        }

    fun scaleWarehouse(warehouse: List<MutableList<Char>>): List<MutableList<Char>> = warehouse.map {
        it.joinToString(separator = "") { tile ->
            when (tile) {
                '#' -> "##"
                'O' -> "[]"
                '.' -> ".."
                else -> "@."
            }
        }.toCharArray().toMutableList()
    }

    fun moves(input: List<String>): List<Char> = input
        .filter {
            it.startsWith("<").or(it.startsWith(">")).or(it.startsWith("^")).or(it.startsWith("v"))
        }.joinToString(separator = "").toCharArray().toList()

    fun findBot(warehouse: List<MutableList<Char>>): Pair<Int, Int> {
        warehouse.forEachIndexed { indexR, row ->
            row.forEachIndexed { indexC, c ->
                if (c == '@')
                    return Pair(indexR, indexC)
            }
        }
        return Pair(0, 0)
    }

    fun move(move: Char, currentPos: Pair<Int, Int>, map: List<MutableList<Char>>): Boolean {
        var newR = currentPos.first
        var newC = currentPos.second
        when (move) {
            '>' -> newC = currentPos.second + 1
            '<' -> newC = currentPos.second - 1
            '^' -> newR = currentPos.first - 1
            'v' -> newR = currentPos.first + 1
        }
        val canMove = if (map[newR][newC] == '#')
            false
        else if (map[newR][newC] == '.')
            true
        else if (map[newR][newC] == '[' && (move == '^' || move == 'v')) {
            move(move, Pair(newR, newC), map)
            move(move, Pair(newR, newC + 1), map)
        } else if (map[newR][newC] == ']' && (move == '^' || move == 'v')) {
            move(move, Pair(newR, newC), map)
            move(move, Pair(newR, newC - 1), map)
        } else
            move(move, Pair(newR, newC), map)

        if (canMove) {
            map[newR][newC] = map[currentPos.first][currentPos.second]
            map[currentPos.first][currentPos.second] = '.'
        }
        return canMove
    }

    fun canMove(move: Char, currentPos: Pair<Int, Int>, map: List<MutableList<Char>>): Boolean {
        var newR = currentPos.first
        var newC = currentPos.second
        when (move) {
            '>' -> newC = currentPos.second + 1
            '<' -> newC = currentPos.second - 1
            '^' -> newR = currentPos.first - 1
            'v' -> newR = currentPos.first + 1
        }
        if (map[newR][newC] == '#')
            return false
        if (map[newR][newC] == '.')
            return true

        return if (move == '<' || move == '>')
            canMove(move, Pair(newR, newC), map)
        else if (map[newR][newC] == '[')
            canMove(move, Pair(newR, newC), map) && canMove(move, Pair(newR, newC + 1), map)
        else
            canMove(move, Pair(newR, newC), map) && canMove(move, Pair(newR, newC - 1), map)
    }

    fun calcRes(warehouse: List<MutableList<Char>>): Int {
        var sum = 0
        warehouse.forEachIndexed { indexR, row ->
            row.forEachIndexed { indexC, c ->
                if (c == 'O' || c == '[') {
                    sum += 100 * indexR + indexC
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val warehouse = warehouseMap(input)
        moves(input).forEach { move ->
            val botPos = findBot(warehouse)
            move(move, botPos, warehouse)
        }
        return calcRes(warehouse)
    }

    fun part2(input: List<String>): Int {
        val warehouse: List<MutableList<Char>> = scaleWarehouse(warehouseMap(input))
        moves(input).forEach { move ->
            val botPos = findBot(warehouse)
            if (canMove(move, botPos, warehouse)) {
                move(move, botPos, warehouse)
            }
        }
        return calcRes(warehouse)
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
