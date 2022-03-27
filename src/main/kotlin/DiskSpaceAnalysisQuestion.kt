import java.util.Stack

fun main(args: Array<String>) {
    println(findMax(arrayOf(2, 5, 4, 6, 8), 3))
}

fun findMax(space: Array<Int>, x: Int): Int {
    var chunkNum = 1
    val s = Stack<Int>()
    s.push(0)
    for (i in space.indices) {
        // first chunk
        if (i < x) {
            if (space[i] < space[s.peek()]) {
                s.pop();
                s.push(i);
            }
        }
        // other chunks
        else {
            // if found minimum is member of current chunk we just need to compare current number with it
            val peek = s.peek();
            if (peek >= chunkNum) {
                s.push(if (space[i] < space[peek])  i else peek);
            }
            // we have to loop through current chunk to find minimum number
            else {
                s.push(i);

                var j = chunkNum;
                var count = 0;
                while (count++ < x) {
                    if (space[j] < space[s.peek()]) {
                        s.pop();
                        s.push(j);
                    }
                    j++;
                }
            }
            // we are ready to go to next chunk
            chunkNum++;
        }
    }

    return s.maxOf { c-> space[c] };
}
