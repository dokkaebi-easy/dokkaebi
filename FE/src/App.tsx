import { Switch, Route } from 'react-router-dom';
import BasicLayout from 'Components/Layouts/BasicLayout/BasicLayout';
import CssBaseline from '@mui/material/CssBaseline';
import SignUp from 'Components/Pages/SignUp/SignUp';
import Home from 'Components/Pages/Home/Home';
import Login from 'Components/Pages/Login/Login';

function App() {
  return (
    <>
      <CssBaseline />
      <Switch>
        <Route path="/signup">
          <SignUp />
        </Route>
        <Route path="/login">
          <Login />
        </Route>
        <Route path="/">
          <BasicLayout />
        </Route>
      </Switch>
    </>
  );
}

export default App;
