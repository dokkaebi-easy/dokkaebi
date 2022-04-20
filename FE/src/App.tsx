import { BrowserRouter, Switch, Route } from 'react-router-dom';
import HomeLayout from 'Components/Layouts/HomeLayout/HomeLayout';
import LoginLayout from 'Components/Layouts/LoginLayout/LoginLayout';
import SignUp from 'Components/Pages/SignUp/SignUp';
import Login from 'Components/Pages/Login/Login';

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" component={LoginLayout} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
