import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { ConnectedRouter as Router } from 'connected-react-router';
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
        <Route path="/">
          <Login />
        </Route>
      </Switch>
    </>
  );
}

export default App;
