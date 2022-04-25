import Box from '@mui/material/Box';
<<<<<<< HEAD
import Grid from '@mui/material/Grid';
import BuildList from '../BuildList/BuildList';

export default function Home() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs />
        <BuildList />
        <Grid item xs={8} />
      </Grid>
=======
import MDTable from 'Components/UI/Molecules/MDTable/MDTable';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import CreateIcon from '@mui/icons-material/Create';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <Box sx={{ marginTop: 5 }}>
      <MDTable />
      <Stack mt={2} direction="row" justifyContent="flex-end" spacing={2}>
        <Button variant="outlined" startIcon={<DeleteIcon />}>
          Delete
        </Button>
        <Link to="/setting">
          <Button variant="contained" endIcon={<CreateIcon />}>
            Create
          </Button>
        </Link>
      </Stack>
>>>>>>> 7cbcd051e1d0cf3ea302f46741e0cb974e7187ba
    </Box>
  );
}
