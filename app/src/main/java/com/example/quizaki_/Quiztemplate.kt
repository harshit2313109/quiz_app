package com.example.quizaki_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.mutableListOf
import kotlin.jvm.java

class Quiztemplate : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var questionList: List<QuizQuestion_dc> = emptyList()
    private var score = 0
    val   selectedAnswers = mutableListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiztemplate)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val category = intent.getStringExtra("category") ?: ""

        val retrofit = Retrofit.Builder()
            .baseUrl("https://yourapi.com/") // change to actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(home_rv_interface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val questions = api.getQuestions().filter { it.category == category }
                withContext(Dispatchers.Main) {
                    if (questions.isNotEmpty()) {
                        questionList = questions
                        currentQuestionIndex = 0

                        val progressBar = findViewById<ProgressBar>(R.id.progressBar4)
                        progressBar.max = questionList.size
                        progressBar.progress = 1

                        showQuestion(questionList[currentQuestionIndex])
                    } else {
                        fallbackToDummy(category)
                    }
                }
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    fallbackToDummy(category)
                }
            }
        }
    }


        fun showQuestion(q: QuizQuestion_dc) {


            selectedAnswers.clear()

            val optionViews = listOf(
                findViewById<LinearLayout>(R.id.option1),
                findViewById<LinearLayout>(R.id.option2),
                findViewById<LinearLayout>(R.id.option3),
                findViewById<LinearLayout>(R.id.option4)
            )

            for ((i, optionView) in optionViews.withIndex()) {
                optionView.setBackgroundResource(R.drawable.nav_bg)

                optionView.setOnClickListener {
                    val optionText = q.options[i]
                    if (selectedAnswers.contains(optionText)) {
                        selectedAnswers.remove(optionText)
                    } else {
                        selectedAnswers.add(optionText)
                    }

                    // Refresh all option backgrounds based on selection
                    for ((j, view) in optionViews.withIndex()) {
                        val text = q.options[j]
                        if (selectedAnswers.contains(text)) {
                            view.setBackgroundResource(R.drawable.edittext_background)
                        } else {
                            view.setBackgroundResource(R.drawable.nav_bg)
                        }
                    }
                }
            }


            findViewById<TextView>(R.id.Questionhere).text = q.question
            findViewById<TextView>(R.id.optionA).text = q.options[0]
            findViewById<TextView>(R.id.optionB).text = q.options[1]
            findViewById<TextView>(R.id.optionC).text = q.options[2]
            findViewById<TextView>(R.id.optionD).text = q.options[3]

            findViewById<TextView>(R.id.questionnum).text = "${currentQuestionIndex + 1}"



            val nextbtn : ImageView = findViewById(R.id.nextques_button)

           nextbtn.setOnClickListener {
                if (selectedAnswers.isEmpty()) {
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (selectedAnswers.toSet() == q.correctans.toSet()) {
                    score++
                }


                if (currentQuestionIndex < questionList.size - 1) {
                    currentQuestionIndex++
                    showQuestion(questionList[currentQuestionIndex])
                }
                else {
                    Toast.makeText(this, "Quiz finished!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,success_page::class.java)
                    intent.putExtra("score", score)
                    startActivity(intent)
                    finish()
                }
            }
            val progressBar = findViewById<ProgressBar>(R.id.progressBar4)
            progressBar.progress = currentQuestionIndex + 1

        }


        fun fallbackToDummy(category: String) {
            val dummyQuestions = getDummyQuestions().filter { it.category == category }
            if (dummyQuestions.isNotEmpty()) {
                questionList = dummyQuestions
                currentQuestionIndex = 0

                val progressBar = findViewById<ProgressBar>(R.id.progressBar4)
                progressBar.max = questionList.size
                progressBar.progress = 1


                showQuestion(questionList[currentQuestionIndex])
            } else {
                Toast.makeText(this, "No questions found", Toast.LENGTH_SHORT).show()
            }
        }


        fun getDummyQuestions(): List<QuizQuestion_dc> {
            return listOf(
                // --- Python ---
                QuizQuestion_dc(
                    "Python",
                    "What is Python?",
                    listOf("Language",),
                    listOf("Language", "Snake", "IDE", "Compiler")
                ),
                QuizQuestion_dc(
                    "Python",
                    "Who developed Python?",
                    listOf("Guido van Rossum"),
                    listOf("Guido van Rossum", "James Gosling", "Linus Torvalds", "Dennis Ritchie")
                ),
                QuizQuestion_dc(
                    "Python",
                    "Which keyword is used to define a function?",
                    listOf("def"),
                    listOf("def", "function", "define", "func")
                ),
                QuizQuestion_dc(
                    "Python",
                    "Which data type is immutable?",
                    listOf("Tuple"),
                    listOf("Tuple", "List", "Set", "Dictionary")
                ),
                QuizQuestion_dc(
                    "Python",
                    "What does PEP stand for?",
                    listOf("Python Enhancement Proposal"),
                    listOf(
                        "Python Enhancement Proposal",
                        "Python Enterprise Program",
                        "Programming Easy Python",
                        "None of the above")
                ),

                // --- Java ---
                QuizQuestion_dc(
                    "Java",
                    "What is JVM?",
                    listOf("Java Virtual Machine"),
                    listOf("Java Virtual Machine", "Java Volume Manager", "Joint VM", "None")
                ),
                QuizQuestion_dc(
                    "Java",
                    "Which company owns Java?",
                    listOf("Oracle"),
                    listOf("Oracle", "Google", "Microsoft", "IBM")
                ),
                QuizQuestion_dc(
                    "Java",
                    "Which keyword is used to inherit a class?",
                    listOf("extends"),
                    listOf("extends", "inherits", "super", "instanceof")
                ),
                QuizQuestion_dc(
                    "Java",
                    "What is bytecode?",
                    listOf("Intermediate code"),
                    listOf("Intermediate code", "Binary code", "Native code", "None")
                ),
                QuizQuestion_dc(
                    "Java",
                    "Which method is the entry point in Java?",
                    listOf( "main()"),
                    listOf("main()", "start()", "run()", "init()")
                ),

                // --- C++ ---
                QuizQuestion_dc(
                    "C++",
                    "What is C++?",
                    listOf("Programming Language"),
                    listOf("Programming Language", "Game", "IDE", "Framework")
                ),
                QuizQuestion_dc(
                    "C++",
                    "Who created C++?",
                    listOf("Bjarne Stroustrup"),
                    listOf("Bjarne Stroustrup", "Guido Rossum", "James Gosling", "Ken Thompson")
                ),
                QuizQuestion_dc(
                    "C++",
                    "Which symbol is used for scope resolution?",
                    listOf("::"),
                    listOf("::", ".", "->", "#")
                ),
                QuizQuestion_dc(
                    "C++",
                    "Which concept supports function overloading?",
                    listOf("Polymorphism"),
                    listOf("Polymorphism", "Abstraction", "Encapsulation", "Inheritance")
                ),
                QuizQuestion_dc(
                    "C++",
                    "Which of the following is not a C++ keyword?",
                    listOf("function"),
                    listOf("function", "class", "namespace", "template")
                ),

                // --- Ruby ---
                QuizQuestion_dc(
                    "Ruby",
                    "What type of language is Ruby?",
                    listOf("Interpreted"),
                    listOf("Interpreted", "Compiled", "Bytecode", "Hybrid")
                ),
                QuizQuestion_dc(
                    "Ruby",
                    "Who created Ruby?",
                    listOf("Yukihiro Matsumoto"),
                    listOf("Yukihiro Matsumoto", "Brendan Eich", "Dennis Ritchie", "James Gosling")
                ),
                QuizQuestion_dc(
                    "Ruby",
                    "What symbol starts a variable in Ruby?",
                    listOf("@"),
                    listOf("@", "$", "#", "&")
                ),
                QuizQuestion_dc(
                    "Ruby",
                    "Which framework is Ruby famous for?",
                    listOf("Rails"),
                    listOf("Rails", "Spring", "Django", "React")
                ),
                QuizQuestion_dc(
                    "Ruby",
                    "What is used to define a method?",
                    listOf("def"),
                    listOf("def", "function", "method", "fun")
                ),

                // --- GoLang ---
                QuizQuestion_dc(
                    "GoLang",
                    "Who developed Go?",
                    listOf("Google"),
                    listOf("Google", "Microsoft", "Oracle", "Facebook")
                ),
                QuizQuestion_dc(
                    "GoLang",
                    "What is the file extension for Go files?",
                    listOf(".go"),
                    listOf(".go", ".golang", ".g", ".gl")
                ),
                QuizQuestion_dc(
                    "GoLang",
                    "Which keyword declares a variable?",
                    listOf("var"),
                    listOf("var", "int", "let", "define")
                ),
                QuizQuestion_dc(
                    "GoLang",
                    "What is Go known for?",
                    listOf("Concurrency"),
                    listOf("Concurrency", "OOP", "Scripting", "Web Design")
                ),
                QuizQuestion_dc(
                    "GoLang",
                    "Does Go support garbage collection?",
                    listOf("Yes"),
                    listOf("Yes", "No", "Partially", "Only in Linux")
                ),

                // --- JavaScript ---
                QuizQuestion_dc(
                    "JavaScript",
                    "Where does JavaScript run?",
                    listOf("Browser"),
                    listOf("Browser", "Compiler", "Editor", "Terminal")
                ),
                QuizQuestion_dc(
                    "JavaScript",
                    "What is '===' in JavaScript?",
                    listOf("Strict equality"),
                    listOf("Strict equality", "Assignment", "Comparison", "Function call")
                ),
                QuizQuestion_dc(
                    "JavaScript",
                    "Which type is not in JS?",
                    listOf("Integer"),
                    listOf("Integer", "Number", "Boolean", "String")
                ),
                QuizQuestion_dc(
                    "JavaScript",
                    "Which company developed JavaScript?",
                    listOf( "Netscape"),
                    listOf("Netscape", "Microsoft", "Apple", "Google")
                ),
                QuizQuestion_dc(
                    "JavaScript",
                    "Which method converts JSON to an object?",
                    listOf("JSON.parse()"),
                    listOf("JSON.parse()", "JSON.stringify()", "parseJSON()", "objectify()")
                )
            )
        }



}