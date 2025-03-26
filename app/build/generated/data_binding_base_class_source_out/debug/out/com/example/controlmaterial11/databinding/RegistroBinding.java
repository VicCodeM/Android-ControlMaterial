// Generated by view binder compiler. Do not edit!
package com.example.controlmaterial11.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.controlmaterial11.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RegistroBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button btnregistrar;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final LinearLayout main;

  @NonNull
  public final TextView textView;

  @NonNull
  public final EditText txtIdempleado;

  @NonNull
  public final EditText txtclave;

  @NonNull
  public final EditText txtnombreusuario;

  private RegistroBinding(@NonNull ScrollView rootView, @NonNull Button btnregistrar,
      @NonNull ImageView imageView, @NonNull LinearLayout main, @NonNull TextView textView,
      @NonNull EditText txtIdempleado, @NonNull EditText txtclave,
      @NonNull EditText txtnombreusuario) {
    this.rootView = rootView;
    this.btnregistrar = btnregistrar;
    this.imageView = imageView;
    this.main = main;
    this.textView = textView;
    this.txtIdempleado = txtIdempleado;
    this.txtclave = txtclave;
    this.txtnombreusuario = txtnombreusuario;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static RegistroBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RegistroBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.registro, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RegistroBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnregistrar;
      Button btnregistrar = ViewBindings.findChildViewById(rootView, id);
      if (btnregistrar == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.main;
      LinearLayout main = ViewBindings.findChildViewById(rootView, id);
      if (main == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.txt_idempleado;
      EditText txtIdempleado = ViewBindings.findChildViewById(rootView, id);
      if (txtIdempleado == null) {
        break missingId;
      }

      id = R.id.txtclave;
      EditText txtclave = ViewBindings.findChildViewById(rootView, id);
      if (txtclave == null) {
        break missingId;
      }

      id = R.id.txtnombreusuario;
      EditText txtnombreusuario = ViewBindings.findChildViewById(rootView, id);
      if (txtnombreusuario == null) {
        break missingId;
      }

      return new RegistroBinding((ScrollView) rootView, btnregistrar, imageView, main, textView,
          txtIdempleado, txtclave, txtnombreusuario);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
