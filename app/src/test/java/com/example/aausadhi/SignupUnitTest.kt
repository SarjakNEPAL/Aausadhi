package com.example.aausadhi

import com.example.aausadhi.repository.SignUpTestRepoImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SignupUnitTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    private lateinit var authRepo: SignUpTestRepoImpl

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<AuthResult>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockAuth = mock(FirebaseAuth::class.java)
        mockTask = mock(Task::class.java) as Task<AuthResult>
        authRepo = SignUpTestRepoImpl(mockAuth)
        `when`(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)
    }

    @Test
    fun testRegistration_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedResult = "Initial Value"

        // Mocking task to simulate successful registration
        `when`(mockTask.isSuccessful).thenReturn(true)

        // Define a callback that updates the expectedResult
        val callback = { success: Boolean, message: String?, ID: String ->
            expectedResult = message ?: "Callback message is null"
        }

        // Call the function under test
        authRepo.signup(email, password, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        // Assert the result
        assertEquals("RegistrationSuccess", expectedResult)
    }
}
