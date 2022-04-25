import { BrowserRouter, Switch, Route } from 'react-router-dom';
<<<<<<< HEAD
import LoginLayout from 'Components/Layouts/LoginLayout/LoginLayout';
import HomeLayout from 'Components/Layouts/HomeLayout/HomeLayout';
import SignUp from 'Components/Pages/SignUp/SignUp';
import Login from 'Components/Pages/Login/Login';
import Home from 'Components/Pages/Home/Home';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Switch>
          <Route path="/" component={HomeLayout} />
          <Route path="/signup" component={SignUp} />
        </Switch>
      </BrowserRouter>
    </div>
=======
import BasicLayout from 'Components/Layouts/BasicLayout/BasicLayout';
import CssBaseline from '@mui/material/CssBaseline';

function App() {
  return (
    <>
      <CssBaseline />
      <Switch>
        <Route path="/">
          <BasicLayout />
        </Route>
      </Switch>
    </>
>>>>>>> 7cbcd051e1d0cf3ea302f46741e0cb974e7187ba
  );
}

export default App;
