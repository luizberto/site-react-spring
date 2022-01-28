import React from 'react';
import 'toastr/build/toastr.min.js';

import 'bootswatch/dist/flatly/bootstrap.css' 
import '../custom.css'
import Rotas from './rotas';
import Navbar from '../components/navbar';

import 'toastr/build/toastr.css';


class App extends React.Component {

  render(){ 
    return(
      <>
      <Navbar/>
      <div className = "container">
      <Rotas />
      </div>
      
      </>
     
    );
  }
}

export default App;
