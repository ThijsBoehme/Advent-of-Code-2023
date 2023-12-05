fun main() {
    fun parseInput(input: List<String>) =
        input.associate { line ->
            val id = """Game (\d+)""".toRegex()
                .find(line)!!
                .groupValues[1]
                .toInt()
            val hands = line.dropWhile { it != ':' }
                .split(";")
                .map { it.trim() }
                .map {
                    val red = """(\d+) red""".toRegex()
                        .find(it)
                        ?.groupValues?.get(1)
                        ?.toInt() ?: 0
                    val blue = """(\d+) blue""".toRegex()
                        .find(it)
                        ?.groupValues?.get(1)
                        ?.toInt() ?: 0
                    val green = """(\d+) green""".toRegex()
                        .find(it)
                        ?.groupValues?.get(1)
                        ?.toInt() ?: 0

                    Triple(red, green, blue)
                }

            id to hands
        }

    fun part1(input: List<String>): Int {
        return parseInput(input)
            .filter { (_, hands) ->
                hands.all { (red, green, blue) ->
                    red <= 12
                            && green <= 13
                            && blue <= 14
                }
            }
            .keys
            .sum()
    }

    fun part2(input: List<String>): Int {
        return parseInput(input)
            .values
            .sumOf { games ->
                val red = games.maxOf { it.first }
                val green = games.maxOf { it.second }
                val blue = games.maxOf { it.third }

                red * green * blue
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
