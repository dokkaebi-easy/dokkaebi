import { BrowserRouter, Switch, Route } from 'react-router-dom';
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
  );
}

export default App;
