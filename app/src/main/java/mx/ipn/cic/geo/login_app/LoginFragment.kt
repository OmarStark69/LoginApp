package mx.ipn.cic.geo.login_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import mx.ipn.cic.geo.login_app.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userEmail = binding.editEmail.text
        val userPassword = binding.editPassword.text

        binding.buttonCrearCuenta.setOnClickListener {
            // Acción para crear cuenta.

            firebaseAuth.createUserWithEmailAndPassword(userEmail.toString(),userPassword.toString()).
            addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(context, "Registro adecuado", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(context, "No se pudo registrar al usuario", Toast.LENGTH_LONG).show()
                }
            }
        }
        // Se agrega el listener para el action de mostrar el mapa.
        binding.buttonIniciarSesion.setOnClickListener {
            firebaseAuth.signInWithEmailAndPassword(userEmail.toString(),userPassword.toString()).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(context, "Atenticación Exitosa", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(context, "Fallo en la autenticación", Toast.LENGTH_LONG).show()
                }
            }
            // TODO: Agregar validación del nombre de usuario y contraseña (Firebase)
            findNavController().navigate(R.id.action_LoginFragment_to_MapsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}