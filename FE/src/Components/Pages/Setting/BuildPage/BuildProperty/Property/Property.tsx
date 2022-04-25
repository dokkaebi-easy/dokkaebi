import React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import DeleteIcon from '@mui/icons-material/Delete';
import Button from '@mui/material/Button';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';

const PropertyItem = ['Publish', 'Volume', 'Evn', 'Build'];

export default function Property() {
  return (
    <Grid container spacing={2}>
      <Grid item>
        <Typography>Property</Typography>
        <SelectItem label="Property" Items={PropertyItem} />
      </Grid>
      <Grid item>
        <Typography>Data1</Typography>
        <TextField
          id="outlined-basic"
          label="Data1"
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          placeholder="Data1"
        />
      </Grid>
      <Grid item>
        <Typography>Data2</Typography>
        <TextField
          id="outlined-basic"
          label="Data2"
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          placeholder="Data2"
        />
      </Grid>
      <Grid item sx={{ marginBottom: 1, marginTop: 'auto' }}>
        <Button variant="outlined" startIcon={<DeleteIcon />} size="small">
          Delete
        </Button>
      </Grid>
    </Grid>
  );
}
