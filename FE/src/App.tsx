import { BrowserRouter, Switch, Route } from 'react-router-dom';
import HomeLayout from 'Components/Layouts/HomeLayout/HomeLayout';

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/">
          <HomeLayout />
        </Route>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
