import kotlin.random.Random

fun main() {
    //Here we initialize variables and our word bank.
    var incorrectGuesses = 7
    val guessedLetters = mutableListOf("")
    val wordList = arrayOf("detach","chase","coward","happen","drone","planet","entry","mountain","wormhole")
    val randomWord = wordList[Random.nextInt(0,wordList.size-1)]
    var userGuess: String

    /* This is the bulk of the logic, where we break down the users guess and send them to different functions to return
     * the results we want.
     */
    println("Welcome to a hangman game completely built in the Kotlin Language.")
    printGallows(incorrectGuesses,randomWord, emptyList())
    do {
        println("\nPlease enter a letter.")
        userGuess = readlnOrNull().toString()
        when (userGuess) {
            in guessedLetters -> {
                println("You've already guessed that.")
            }

            in randomWord -> {
                guessedLetters += userGuess
                printGallows(incorrectGuesses,randomWord,guessedLetters)
            }

            else -> {
                guessedLetters += userGuess
                incorrectGuesses -= 1
                printGallows(incorrectGuesses,randomWord,guessedLetters)
            }
        }

        val checker: Boolean = win(randomWord,guessedLetters)
    } while (!checker && incorrectGuesses > 0)
    println("Game over!")
    println("The correct answer was ${randomWord.uppercase()}.")
}

fun printGallows(incorrectGuesses: Int, word: String, guessedLetters: List<String>) {
    //This when statement prints the different stages of the hangman being built.
    when (incorrectGuesses) {
        7 -> println(" _____"+"\n |    |"+"\n      |"+"\n      |"+"\n      |"+"\n      |"+"\n_______")
        6 -> println(" _____"+"\n |    |"+"\n O    |"+"\n      |"+"\n      |"+"\n      |"+"\n_______")
        5 -> println(" _____"+"\n |    |"+"\n O    |"+"\n |    |"+"\n      |"+"\n      |"+"\n_______")
        4 -> println(" _____"+"\n |    |"+"\n O    |"+"\n\\|    |"+"\n      |"+"\n      |"+"\n_______")
        3 -> println(" _____"+"\n |    |"+"\n O    |"+"\n\\|/   |"+"\n      |"+"\n      |"+"\n_______")
        2 -> println(" _____"+"\n |    |"+"\n O    |"+"\n\\|/   |"+"\n |    |"+"\n      |"+"\n_______")
        1 -> println(" _____"+"\n |    |"+"\n O    |"+"\n\\|/   |"+"\n |    |"+"\n/    |"+"\n_______")
        0 -> println(" _____"+"\n |    |"+"\n O    |"+"\n\\|/   |"+"\n |    |"+"\n/ \\  |"+"\n_______")
    }

    //Here we print our incorrect letters so the user knows what not to guess again.
    for (letter in guessedLetters)
    {
        if (letter !in word)
        {
            print("$letter ")
        }
    }

    println("\n\nYou have $incorrectGuesses guesses remaining.")
    wordHider(word,guessedLetters)
}

fun wordHider(word: String, guessedLetters:  List<String>) {
    /*For each letter in our hidden word, we are going to see if the person has guessed it,
     * and then can reveal it if they have.
     */
    for (character in word)
    {
        //The only way I could get this to work was if it was a string. Not sure if there's another way around this.
        val letter = character.toString()
        if (letter in guessedLetters)
        {
            print(character)
        }

        else
        {
            print("_")
        }
    }

    print("\n")
}

fun win(word: String,guessedLetters: List<String>): Boolean {
    //Here we can track if we have guessed enough correct letters to make up the word. If so, we can return a true value.
    var checker = 0
    for (character in word)
    {
        val letter = character.toString()
        if (letter in guessedLetters)
        {
            checker += 1
        }
    }

    return checker == word.length
}