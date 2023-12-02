fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line -> line.filter { it.isDigit() } }
            .map { "${it.first()}${it.last()}" }
            .sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val textToDigit = mapOf(
            "zero" to 0,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )

        return input.sumOf { line ->
            val firstOccurrences = textToDigit.keys
                .associateWith { line.indexOf(it) }
                .mapKeys { (key, _) -> textToDigit[key]!! }
                .mapValues { (_, value) -> if (value < 0) Int.MAX_VALUE else value }
                .toMutableMap()

            line.firstOrNull { it.isDigit() }?.digitToInt()
                ?.let {
                    firstOccurrences.replace(
                        it,
                        minOf(firstOccurrences[it]!!, line.indexOf("$it"))
                    )
                }

            val lastOccurrences = textToDigit.keys
                .associateWith { line.lastIndexOf(it) }
                .mapKeys { (key, _) -> textToDigit[key]!! }
                .toMutableMap()
            line.lastOrNull { it.isDigit() }?.digitToInt()
                ?.let {
                    lastOccurrences.replace(
                        it,
                        maxOf(lastOccurrences[it]!!, line.lastIndexOf("$it"))
                    )
                }

            val firstDigit = firstOccurrences.minBy { it.value }.key
            val lastDigit = lastOccurrences.maxBy { it.value }.key
            "$firstDigit$lastDigit"
                .toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day01_test_part1")
    check(part1(testInputPart1) == 142)
    val testInputPart2 = readInput("Day01_test_part2")
    check(part2(testInputPart2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
