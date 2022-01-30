import React from 'react';
import 'toastr/build/toastr.min.js';
import ProvedorAutenticacao from './provedorAutenticacao';

import 'bootswatch/dist/flatly/bootstrap.css' 
import '../custom.css'
import Rotas from './rotas';
import Navbar from '../components/navbar';

import 'toastr/build/toastr.css';

import "primereact/resources/themes/lara-light-indigo/theme.css";  //theme
import "primereact/resources/primereact.min.css";                  //core css
import "primeicons/primeicons.css";                                //icons
 

class App extends React.Component {

  render(){ 
    return(
      <>
      <ProvedorAutenticacao>
      <Navbar/>
      <div className = "container">
      <Rotas />
      </div>
      </ProvedorAutenticacao>
      </>
     
    );
  }
}

export default App;
