package org.cqfn.save.IgnoreLinesTest.IgnoreLines

class D {
    val x = 0

    /**
     * @return
     */
    fun bar(): Bar {
        val qux = 42
        return Bar(qux)
    }
}

/**
 * @param foo
 */
fun readFile(foo: Foo) {
    var bar: Bar
}
// ;warn:0: [TEST] JUST_A_TEST
